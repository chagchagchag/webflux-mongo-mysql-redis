window.onload = function(){
  const eventSource = new EventSource('http://localhost:8080/vote2');

  eventSource.onopen = event => {
    console.log('opened');
  }

  eventSource.addEventListener('vote', event => {
    const type = event.type;
    const id = event.lastEventId;
    const data = event.data;
    console.log(`type: ${type}, id: ${id}, data: ${data}`);
  });
}