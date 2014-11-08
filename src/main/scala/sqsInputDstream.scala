/**
 * Created by prannoy on 19/8/14.
 */
import java.util
import java.util.Map._
import org.apache.spark.Logging
import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming.receiver._
import com.amazonaws.auth.{BasicAWSCredentials,AWSCredentials}
import com.amazonaws.services.sqs.model._
import com.amazonaws.services.sqs.{AmazonSQS, AmazonSQSClient}
import scala.collection.JavaConverters._


class sqsInputDstream(queueURL: String, accessKey: String, secretKey: String)
  extends Receiver[String](StorageLevel.MEMORY_AND_DISK_2) with Logging{
  def onStart() {
    // Start the thread that receives data over a connection
    new Thread("Socket Receiver") {
      override def run() { receive() }
    }.start()
  }

  def onStop() {
    // There is nothing much to do as the thread calling receive()
    // is designed to stop by itself isStopped() returns false
  }

  private def receive() {
   try {
      val credentials = new BasicAWSCredentials(accessKey,	secretKey)
      val sqs = new AmazonSQSClient(credentials)
      var tempSqsMsg = getSQSmsg(sqs)
      if(!tempSqsMsg.isEmpty){
        tempSqsMsg.foreach(a => store(a.toString))
      }
      while(!isStopped && !tempSqsMsg.isEmpty) {
        //        println(userInput)
        tempSqsMsg.foreach(a => store(a.toString))
        tempSqsMsg = getSQSmsg(sqs)
      }

      logInfo("Stopped receiving")
      // Restart in an attempt to connect again when server is active again
      restart("Trying to connect again")
    } catch {
      case e: java.net.ConnectException =>
        // restart if could not connect to server
        restart("Error connecting to ", e)
      case t: Throwable =>
        // restart if there is any other error
        restart("Error receiving data", t)
    }
  }

  private def getSQSmsg(sqs: AmazonSQS): List[String] = {
    var SqsMsg: List[String] = List()
    val receiveMessageRequest = new ReceiveMessageRequest(queueURL)
    val messages = sqs.receiveMessage(receiveMessageRequest.withAttributeNames("All").withMaxNumberOfMessages(10)).getMessages

    if(messages.size()!=0){
      for(msg <- messages.asScala) {
        var tempSqsMsg = new SqsMessage()
        //        val msg = messages.get(0)

        tempSqsMsg.setMessageId(msg.getMessageId)
        tempSqsMsg.setReceiptHandle(msg.getReceiptHandle)
        tempSqsMsg.setMD5OfBody(msg.getMD5OfBody)
        tempSqsMsg.setBody(msg.getBody)

        val set: util.Set[Entry[String, String]] = msg.getAttributes.entrySet()
        for (entry <- set.asScala) {
          val name: String = entry.getKey
          if (name.contains("ApproximateFirstReceiveTimestamp")) {
            tempSqsMsg.setApproxFirstReceiveTimestamp(entry.getValue)
          } else if (name.contains("SentTimestamp")) {
            tempSqsMsg.setSentTimestamp(entry.getValue)
          } else if (name.contains("ApproximateReceiveCount")) {
            tempSqsMsg.setApproximateReceiveCount(entry.getValue)
          } else if (name.contains("SenderId")) {
            tempSqsMsg.setSenderId(entry.getValue)
          }
        }

        SqsMsg++=List(tempSqsMsg.getToStringSQSmessage)
//        if (tempSqsMsg != null) SqsMsg+(tempSqsMsg.getToStringSQSmessage)
//        else SqsMsg = tempSqsMsg.getToStringSQSmessage + "\n"
//        sqs.deleteMessage(new DeleteMessageRequest().withQueueUrl(queueURL)
//          .withReceiptHandle(msg.getReceiptHandle()));
      }
    }
    SqsMsg
  }
}


