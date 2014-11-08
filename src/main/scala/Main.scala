/**
 * Created by lalit on 1/8/14.
 */

import org.apache.spark.streaming._
import org.apache.spark.SparkConf

object Main {
  def main(args: Array[String]) {

    val conf = new SparkConf()
   .setMaster("")
      .setAppName("SQS Connector")
      .setSparkHome("")
      .setJars(List("target/scala-2.10/sparktest_2.10-2.0.jar"," PATH_TO_PROJECT_AWS_API_JAR"))

    val queueURL = ""//sqs queue url
    val accessKey = ""//sqs Decret Key
    val secretKey = ""//sqs Decret Key

    val sqsDstream: sqsInputDstream = new sqsInputDstream(queueURL,accessKey,secretKey)
    val ssc = new StreamingContext(conf,  Seconds(5))
    val mesgDStream = ssc.receiverStream(sqsDstream)

    mesgDStream.map(xyz => xyz + "END OF MESSAGE\n\n").print()
//    mesgDStream.foreachRDD(rdd => rdd.foreach(elements => downloader(elements.toString)))
    ssc.start()
    ssc.awaitTermination()
  }

//  def downloader(sqsMsg:String){
//    val queueURL = sqs queue url
//    val accessKey = sqs Decret Key
//    val secretKey = sqs Decret Key
//
//    val parallelDownloading = new ParallelDownloading(sqsMsg,queueURL ,"qdr-prd-output",accessKey,secretKey)
//    parallelDownloading.run()
//  }
}