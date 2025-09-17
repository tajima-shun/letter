/**
 * main.jsp用のJavaScript
 */

document.getElementById('letterForm').addEventListener('submit', function(event) {
	var message = document.getElementById('message').value;
	if (message.trim() === '') {
		alert('メッセージを入力してください。');
		event.preventDefault(); // フォームの送信をキャンセル
	}
});
