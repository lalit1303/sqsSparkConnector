/**
 * Created by prannoy on 19/8/14.
 */
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SqsMessage {
    String MessageId, ReceiptHandle, MD5OfBody, Body;
    String ApproxFirstReceiveTimestamp;
    String SentTimestamp;
    String ApproximateReceiveCount;
    String SenderId;
    Long FormattedTimeStamp;

    public String getToStringSQSmessage() {
        String messg;
        messg = this.MessageId + "\n";
        messg = messg + this.ReceiptHandle + "\n";
        messg = messg + this.MD5OfBody + "\n";
        messg = messg + this.Body + "\n";
        messg = messg + new Date(Long.parseLong(this.ApproxFirstReceiveTimestamp)) + "\n";
        messg = messg + new Date(Long.parseLong(this.SentTimestamp)) + "\n";
        messg = messg + this.ApproximateReceiveCount + "\n";
        messg = messg + this.SenderId + "\n";
        return messg;
    }

    public void printSQSmessage(FileWriter fw ){
        try{
            fw.write("MessageId: " + this.MessageId + "\n");
            fw.write("ReceiptHandle: " + this.ReceiptHandle + "\n");
            fw.write("MD5OfBody: " + this.MD5OfBody + "\n");
            fw.write("Body: " + this.Body + "\n");
            fw.write("ApproxFirstReceiveTimestamp: " + new Date(Long.parseLong(this.ApproxFirstReceiveTimestamp)) + "\n");
            fw.write("SentTimestamp: " + new Date(Long.parseLong(this.SentTimestamp)) + "\n");
            fw.write("ApproximateReceiveCount: " + this.ApproximateReceiveCount + "\n");
            fw.write("SenderId: " + this.SenderId + "\n");
        }catch(Exception e){ e.printStackTrace();
        }
    }

    public String getFormattedTimeStamp() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
        long timeStampInLong = Long.parseLong(this.getSentTimestamp());
        return sdf.format(new Date(timeStampInLong));
    }

    public void setFormattedTimeStamp(Long formattedTimeStamp) {
        this.FormattedTimeStamp = formattedTimeStamp;
    }

    public void setMessageId(String str) {
        this.MessageId = str;
    }

    public String getMessageId() {
        return this.MessageId;
    }

    public void setReceiptHandle(String str) {
        this.ReceiptHandle = str;
    }

    public String getReceiptHandle() {
        return this.ReceiptHandle;
    }

    public void setMD5OfBody(String str) {
        this.MD5OfBody = str;
    }

    public String getMD5OfBody() {
        return this.MD5OfBody;
    }

    public void setBody(String str) {
        this.Body = str;
    }

    public String getBody() {
        return this.Body;
    }

    public void setApproxFirstReceiveTimestamp(String str) {
        this.ApproxFirstReceiveTimestamp = str;
    }

    public String getApproxFirstReceiveTimestamp() {
        return ApproxFirstReceiveTimestamp;
    }

    public void setSentTimestamp(String str) {
        this.SentTimestamp = str;
    }

    public String getSentTimestamp() {
        return SentTimestamp;
    }

    public String getApproximateReceiveCount() {
        return ApproximateReceiveCount;
    }

    public void setApproximateReceiveCount(String str) {
        this.ApproximateReceiveCount = str;
    }

    public String getSenderId() {
        return SenderId;
    }

    public void setSenderId(String str) {
        SenderId = str;
    }
}


