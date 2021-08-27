let logoutButton = document.getElementById('logout');
let filterButton = document.getElementById('filterButton');
let filterInput = document.getElementById('type');
let removeFilterButton = document.getElementById('remove');

function onLoad(event) {
    fetch('http://localhost:7000/currentuser', {
        'credentials': 'include',
        'method': 'GET'

    }).then((response) => {
        if (response.status === 401) {
            window.location.href = '/index.html'
        } else if (response.status === 200) {
            return response.json();
        }
    }).then((user) => {
        return fetch(`http://localhost:7000/getallrequests`, {
            'method': 'GET',
            'credentials': 'include'
        });

    }).then((response) => {
        return response.json()
    }).then((reimbursements) => {
        populateReimbs(reimbursements);
    })
}

function populateReimbs(reimbArray) {
    let tbody = document.querySelector('#reimbs tbody');

    for (const reimb of reimbArray) {

        let tr = document.createElement('tr');

        let reimbIdTd = document.createElement('td');
        reimbIdTd.innerHTML = reimb.id;

        let reimbAmountTd = document.createElement('td');
        reimbAmountTd.innerHTML =  "$" + reimb.amount;

        let reimbDescriptionTd = document.createElement('td');
        reimbDescriptionTd.innerHTML = reimb.description;


        let reimbAuthorFirstName = document.createElement('td');
        reimbAuthorFirstName.innerHTML = reimb.author.firstName;

        let reimbAuthorLastName = document.createElement('td');
        reimbAuthorLastName.innerHTML = reimb.author.lastName;

        let reimbStatus = document.createElement('td');
        reimbStatus.innerHTML = reimb.status.status;

        var approveButton = null;
        var denyButton = null;
        if (reimbStatus.innerHTML == "pending") {
            approveButton = document.createElement("button");
            approveButton.innerHTML = "Approve";
            approveButton.onclick = function () {
                const submissionInfo = {
                    'status': 2
                };

                fetch(`http://localhost:7000/processrequest/${reimb.id}`, {
                    'credentials': 'include',
                    'method': 'PUT',
                    headers: {
                        'Content-Type': 'application/json' // application/json is a MIME type
                    },
                    body: JSON.stringify(submissionInfo)
                }).then((response) => {
                    if (response.status === 200) {
                        window.location.href = '/managerviewreimb.html'
                    }
                })
            };

            denyButton = document.createElement("button");
            denyButton.innerHTML = "Deny";
            denyButton.onclick = function () {
                const submissionInfo = {
                    'status': 3
                };

                fetch(`http://localhost:7000/processrequest/${reimb.id}`, {
                    'credentials': 'include',
                    'method': 'PUT',
                    headers: {
                        'Content-Type': 'application/json' // application/json is a MIME type
                    },
                    body: JSON.stringify(submissionInfo)
                }).then((response) => {
                    if (response.status === 200) {
                        window.location.href = '/managerviewreimb.html'
                    }
                })
            };

        }

            tr.appendChild(reimbIdTd);
            tr.appendChild(reimbAmountTd);
            tr.appendChild(reimbDescriptionTd);
            tr.appendChild(reimbAuthorFirstName);
            tr.appendChild(reimbAuthorLastName);
            tr.appendChild(reimbStatus);
            if(approveButton != null){
                tr.appendChild(approveButton);
            }
            if(approveButton != null){
                tr.appendChild(denyButton);
            }

            tbody.appendChild(tr);


        }
    }



    function logout(event) {

        event.preventDefault();


        fetch('http://localhost:7000/logout', {
            method: 'POST',
            credentials: 'include',
            headers: {
                'Content-Type': 'application.json'
            },
        }).then((response) => {
            if (response.status === 200) {
                window.location.href = '/index.html';

            }
        })


    };


    function filter(event) {
        event.preventDefault();

        fetch('http://localhost:7000/currentuser', {
            'credentials': 'include',
            'method': 'GET'

        }).then((response) => {
            if (response.status === 401) {
                window.location.href = '/index.html'
            } else if (response.status === 200) {
                return response.json();
            }
        }).then((user) => {
            return fetch(`http://localhost:7000/filterrequestbystatus/${filterInput.value}`, {
                'method': 'GET',
                'credentials': 'include'
            });

        }).then((response) => {
            return response.json()
        }).then((reimbursements) => {
            clearTable();
            populateReimbs(reimbursements);
        })
    }

    function clearTable() {
        let tbody = document.querySelector('#reimbs tbody');
        tbody.innerHTML = "";
    }


    filterButton.addEventListener('click', filter);
    logoutButton.addEventListener('click', logout);
    window.addEventListener('load', onLoad);
    removeFilterButton.addEventListener('click', onLoad);