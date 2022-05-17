// Call the dataTables jQuery plugin
$(document).ready(function() {

   cargarUsuarios();

  $('#usuarios').DataTable();
});

async function cargarUsuarios(){

    const response = await fetch("http://localhost:8080/api/usuario", {
      method: "GET",
      headers: getHeaders(),
    });
    console.log('los headers al cargar',getHeaders(), 'la requerst', {response});

    const usuarios = await response.json();
    console.log({usuarios});

    let listadoHtml = '';
    usuarios.forEach(a => {
         let deleteButoon = '<a href="#" onclick=deleteUser(' + a.id + ') class="btn btn-danger btn-circle btn-sm"><i class="fas fa-trash"></i></a';
         let usuario = `<tr><td>${a.id}</td><td>${a.nombre}</td><td>${a.email}</td><td>${(!(a.telefono === null)) ? a.telefono : ""}</td><td>${deleteButoon}</td></tr>`
         console.log({usuario});
         listadoHtml = listadoHtml + usuario;
    });


    const listOfUsers = document.querySelector("#usuarios tbody").outerHTML = listadoHtml;
};

function getHeaders(){
    return {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
        'Authorization': localStorage.token
    }
}

async function deleteUser(id){

    if(!confirm('¿Desea Eliminar este usuario')){
        return;
    }
    const response = await fetch("http://localhost:8080/api/usuario/" + id, {
          method: "DELETE",
          headers: getHeaders(),
        });
        console.log(response);
    await cargarUsuarios();
};
