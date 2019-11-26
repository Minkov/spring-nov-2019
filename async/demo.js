function getData(readyCallback) {
    setTimeout(() => {
        const data = 'my data';
        readyCallback(data);
    }, 1000);
}

// console.log(getData());
const result = getData(data => console.log(data));