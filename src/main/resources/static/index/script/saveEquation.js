document.getElementById('saveEquationButton').addEventListener('click', function () {
    var equation = document.getElementById('equation').value;
    var message = document.getElementById('messageEquation');

    fetch('http://localhost:8080/api/v1/equation/save', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({equation: equation})
    })
        .then(function (response) {
            if (response.status === 200) {
                return response.text(); // Get the text from the response
            } else if (response.status === 400) {
                return response.text().then(text => {
                    throw new Error(text); // Throw an error with the response text
                });
            }
            throw new Error('Request failed with status ' + response.status);
        })
        .then(function (text) {
            document.getElementById('equation').style.backgroundColor = '#d4edda';
            message.textContent = text;
            message.style.display = 'block';
            message.style.color = 'green';

        })
        .catch(function (error) {
            document.getElementById('equation').style.backgroundColor = '#f8d7da';
            message.textContent = error.message;
            message.style.display = 'block';
            message.style.color = 'red';
        });
});