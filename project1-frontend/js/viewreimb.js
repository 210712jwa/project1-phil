let addReimbButton = document.getElementById('addreimbursement');
let logoutButton = document.getElementById('logout');

function checkIfUserCurrentlyLoggedIn(event){
    fetch ('http://localhost:7000/currentuser',{
        'credentials' : 'include',
        'method' : 'GET'
    }).then((response) => {
        if(response.status === 401){
            window.location.href = '/index.html'
        } else if (response.status === 200){
           return response.json();
            }
        }).then((user) => {
            return fetch(`http://localhost:7000/user/${user.id}/reimbursement`,{
                'method' : 'GET',
                'credentials' : 'include'

            });

        }).then((response) => {
            return response.json()
        }).then((reimbs) => {
            populateReimbs(reimbs);

    })

}

function populateReimbs(reimbArray){
    let tbody = document.querySelector('#reimbs tbody');

    for (const reimb of reimbArray){

        let tr = document.createElement('tr');

        let reimbIdTd = document.createElement('td');
        reimbIdTd.innerHTML = reimb.id;

        let reimbAmountTd = document.createElement('td');
        reimbAmountTd.innerHTML = reimb.amount;

        let reimbDescriptionTd = document.createElement('td');
        reimbDescriptionTd.innerHTML = reimb.description;

        
        let reimbAuthorFirstName = document.createElement('td');
        reimbAuthorFirstName.innerHTML = reimb.author.firstName;
        
        let reimbAuthorLastName = document.createElement('td');
        reimbAuthorLastName.innerHTML = reimb.author.lastName;

        let reimbStatus = document.createElement('td');
        reimbStatus.innerHTML = reimb.status.status;


        tr.appendChild(reimbIdTd);
        tr.appendChild(reimbAmountTd);
        tr.appendChild(reimbDescriptionTd);
        tr.appendChild(reimbAuthorFirstName);
        tr.appendChild(reimbAuthorLastName);
        tr.appendChild(reimbStatus);
     

        tbody.appendChild(tr);





        



    }
}

function addReimbursement(){
    window.location.href = '/addreimb.html'

}

function logout(event){
  
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


logoutButton.addEventListener('click', logout);
addReimbButton.addEventListener('click', addReimbursement);
window.addEventListener('load', checkIfUserCurrentlyLoggedIn)
