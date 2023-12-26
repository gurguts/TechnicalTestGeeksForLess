function checkInputNumber() {
    var input = document.getElementById('numberRootInput').value;
    document.getElementById('findNumberRootEquationsButton').disabled = input.trim() === '';
}

document.getElementById('findNumberRootEquationsButton').addEventListener('click', function() {
    var number = document.getElementById('numberRootInput').value;
    loadAndDisplayRoots('/api/v1/root/find/number/' + number);
});

document.getElementById('findAllRoots').addEventListener('click', function () {
    loadAndDisplayRoots('/api/v1/root/find/all');
});

    document.addEventListener('DOMContentLoaded', function () {
    loadAndDisplayRoots('/api/v1/root/find/all');
});

document.getElementById('findEquationsByRootButton').addEventListener('click', function () {
    var root = document.getElementById('rootInput').value;
    loadAndDisplayRoots('/api/v1/root/find/root/' + root)
});

function checkInput() {
    var input = document.getElementById('rootInput').value;
    document.getElementById('findEquationsByRootButton').disabled = input.trim() === '';
}