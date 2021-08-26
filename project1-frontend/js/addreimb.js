let descriptionInput = document.getElementById('reimbursementdesc');
let amountInput = document.getElementById('reimbursementamt');
let typeInput = document.getElementById('reimbursementtype');
let submitButton = document.getElementById('submit');

function submit(event) {
    event.preventDefault();
    alert("I am here");
    const reimbInfo = {

        'description': descriptionInput.value,
        'amount': amountInput.value,
        'type': typeInput.value
    };

    fetch('http://localhost:7000/currentuser',{
        'credentials': 'include',
        'method' : 'GET'
    }).then((response)=> {
        if (response.status === 401){
            window.location.href = '/index.html'
        } else if (response.status === 200){
            return response.json();
        }
    }).then((user) => {
        

    return fetch(`http://localhost:7000/user/${user.id}/reimbursement`, {
        method: 'POST',
        credentials: 'include',
        headers: {
            'Content-Type': 'application.json'
        },
        body: JSON.stringify(reimbInfo)

    });

}).then((response) => {
    if (response.status === 200){
        window.location.href = '/viewreimb.html';
    }else{
        alert("error adding reimbursement record");
    }

})


};

submitButton.addEventListener('click', submit);