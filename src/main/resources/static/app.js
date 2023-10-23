const stompClient = new StompJs.Client({
  brokerURL: 'ws://localhost:8080/chat/websocket',
});

stompClient.onConnect = frame => {
  setConnected(true);
  console.log('Connected: ' + frame);
  fetch('http://localhost:8080/chat/rest/pick-chat', { method: 'GET' })
    .then(res => res.json())
    .then(data => data.url)
    .then(url => {
      stompClient.subscribe(url, message => {
        console.log(message);

        showGreeting(JSON.parse(message.body).content);
      });
    })
    .catch(err => console.error(err));
};

stompClient.onWebSocketError = error => {
  console.error('Error with websocket', error);
};

stompClient.onStompError = frame => {
  console.error('Broker reported error: ' + frame.headers['message']);
  console.error('Additional details: ' + frame.body);
};

function setConnected(connected) {
  $('#connect').prop('disabled', connected);
  $('#disconnect').prop('disabled', !connected);
  if (connected) {
    $('#conversation').show();
  } else {
    $('#conversation').hide();
  }
  $('#greetings').html('');
}

function connect() {
  stompClient.activate();
}

function disconnect() {
  stompClient.deactivate();
  setConnected(false);
  console.log('Disconnected');
}

function sendMessage() {
  stompClient.publish({
    destination: '/app/message',
    body: JSON.stringify({ content: $('#name').val() }),
  });
}

function showGreeting(message) {
  $('#greetings').append('<tr><td>' + message + '</td></tr>');
}

function showSubscriptions() {
  console.log(stompClient.state);
}

$(function () {
  $('form').on('submit', e => e.preventDefault());
  $('#connect').click(() => connect());
  $('#disconnect').click(() => disconnect());
  $('#send').click(() => sendMessage());
});
