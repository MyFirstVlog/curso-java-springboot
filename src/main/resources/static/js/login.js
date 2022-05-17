// Call the dataTables jQuery plugin
$(document).ready(function() {

// on ready
});

async function iniciarSesion(){

    let datos = {};
    datos.email = document.getElementById('txtEmail').value;
    datos.password = document.getElementById('txtPassword').value;

    console.log({datos});

    const response = await fetch("http://localhost:8080/api/login", {
      method: "POST",
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(datos)
    });
    console.log({response});

    const usuarios = await response.text();
    if(usuarios !== "FAIL" || usuarios){
        localStorage.setItem('token', usuarios);
        localStorage.setItem('email', datos.email);
        window.location.href = 'usuarios.html'
    }else{
        alert("las crendenciales son incorrectas, intente nuevamente !!!")
    }


};
