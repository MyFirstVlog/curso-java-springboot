// Call the dataTables jQuery plugin
$(document).ready(function() {

// on ready
});

async function registrarUsuarios(){

    let datos = {};
    datos.nombre = document.getElementById('txtNombre').value;
    datos.apellido = document.getElementById('txtApellido').value;
    datos.email = document.getElementById('txtEmail').value;
    datos.password = document.getElementById('txtPassword').value;

    let repetirPassword = document.getElementById('txtRepeatPassword').value;

    console.log(repetirPassword, datos.password);

    if(repetirPassword !== datos.password){
        alert('la contraseña ingresada es incorrecta !!!');
        return;
    }

    const response = await fetch("http://localhost:8080/api/usuario", {
      method: "POST",
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(datos)
    });
    console.log(response);



};
