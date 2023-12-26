function toggleRootFields() {
    var checkBox = document.getElementById("rootCheckBox");
    var rootFields = document.getElementById("rootFields");
    rootFields.style.display = checkBox.checked ? "block" : "none";
}

document.getElementById('saveRootsButton').addEventListener('click', function () {
    var equation = document.getElementById('equation').value;
    var root = document.getElementById('root').value;
    var messageEquation = document.getElementById('messageEquation');
    var messageRoots = document.getElementById('messageRoots');

    root = root === '' ? null : root;

    fetch('http://localhost:8080/api/v1/root/save', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            equation: equation,
            root: root
        })
    })
        .then(function (response) {
            if (response.status === 200) {
                return response.json(); // Get the text from the response
            } else if (response.status === 400) {
                return response.json().then(data => {
                    throw data; // Use the server's error message
                });
            }
            throw new Error('Request failed with status ' + response.status);
        })
        .then(function (data) {
            document.getElementById('equation').style.backgroundColor = '#d4edda';
            document.getElementById('root').style.backgroundColor = '#d4edda';

            messageEquation.textContent = data.equationMessage; // Use server's response message
            messageRoots.textContent = data.rootsMessage;

            messageEquation.style.display = 'block';
            messageRoots.style.display = 'block';
            messageEquation.style.color = 'green';
            messageRoots.style.color = 'green';
        })
        .catch(function (data) {
            messageEquation.textContent = data.equationMessage;
            messageRoots.textContent = data.rootsMessage;

            if (data.equationMessage==="Equation correct"){
                document.getElementById('equation').style.backgroundColor = '#d4edda';
            } else{
                document.getElementById('equation').style.backgroundColor = '#f8d7da';
            }
            document.getElementById('root').style.backgroundColor = '#f8d7da';

            messageEquation.style.display = 'block';
            messageRoots.style.display = 'block';
            if (data.equationMessage==="Equation correct"){
                messageEquation.style.color = 'green';
            } else{
                messageEquation.style.color = 'red';
            }
            messageRoots.style.color = 'red';
        });
});