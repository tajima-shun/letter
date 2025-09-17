package model;

import java.sql.Timestamp;

/**----------------------------------------------------------------------*
 *■■■LetterDtoクラス■■■
 *概要：DTO（「letters」テーブル）
 *----------------------------------------------------------------------**/
public class LetterDto {

	//----------------------------------------------------------------
	//フィールド
	//----------------------------------------------------------------
	private int letterId;
	private int senderId;
	private int recipientId;
	private String message;
	private Timestamp createdAt;
	private String senderName; // 送信者の名前を保持するための追加フィールド
	private String recipientName; // 受信者の名前を保持するための追加フィールド
	private String imageFilename; // 画像ファイル名を保持するためのフィールド

	//----------------------------------------------------------------
	//getter/setter
	//----------------------------------------------------------------

	public int getLetterId() {
		return letterId;
	}

	public void setLetterId(int letterId) {
		this.letterId = letterId;
	}

	public int getSenderId() {
		return senderId;
	}

	public void setSenderId(int senderId) {
		this.senderId = senderId;
	}

	public int getRecipientId() {
		return recipientId;
	}

	public void setRecipientId(int recipientId) {
		this.recipientId = recipientId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public String getRecipientName() {
		return recipientName;
	}

	public void setRecipientName(String recipientName) {
		this.recipientName = recipientName;
	}

	public String getImageFilename() {
		return imageFilename;
	}

	public void setImageFilename(String imageFilename) {
		this.imageFilename = imageFilename;
	}
}
