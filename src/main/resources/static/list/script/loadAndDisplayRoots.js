function loadAndDisplayRoots(url) {
    fetch(url)
        .then(response => response.json())
        .then(roots => {
            const tableBody = document.getElementById('rootsTable').querySelector('tbody');
            tableBody.innerHTML = '';
            roots.forEach(root => {
                let row = `<tr>
                               <td>${root.id}</td>
                               <td>${root.equation.equation}</td>
                               <td>${root.root}</td>
                           </tr>`;
                tableBody.innerHTML += row;
            });
        });
}