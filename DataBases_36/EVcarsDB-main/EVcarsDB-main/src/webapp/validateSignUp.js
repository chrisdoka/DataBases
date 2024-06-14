function onChange() {
    const password = document.getElementById("password").value;
    const confirm = document.getElementById("confirm").value;
    test(password);
 
    if (confirm.value === password.value) {
        confirm.setCustomValidity('');
    } else {
	confirm.setCustomValidity('Passwords do not match');
    }
}
		

		
function test(password)
{
    var x=password.value.length;
    var y=password.value;
    const specialChars = /[`!@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?~]/;
    var z=specialChars.length;
    var digitcount=0;
    var uppercount=0;
    var lowcount=0;
    var symbolcount=0;
    for(var i=0; i<x;i++) {
        if(!isNaN(y[i])) {
                digitcount++;
        }
        else if(y[i].toUpperCase()) {
                uppercount++;
        }
        else if(y[i].toLowerCase()){
                lowcount++;
        }
        for(var j=0;j<z;j++) {
            if(y[i]==specialChars[z]){
            symbolcount++;
            }
        }
    }
    if((x/2)<digitcount)
    {
	//password.setCustomValidity('Weak password');
    }
    else if(symbolcount>1 && uppercount>0 && lowcount>0)
    {
	alert("Strong Password!");
    }
    else
    {
	alert("Medium Password!");
    }
}
		
function checkIfChecked()
{
    var checkbox = document.getElementById('terms');
    if (checkbox.checked !== true)
    {
	alert("you need to check the box to continue");
    }
}
                            
function registerPost()
{
    let myForm = document.getElementById("firstForm");
    let formData = new FormData(myForm);
    const data = {};
    formData.forEach((value,key) => (data[key] = value));
    var jsonData = JSON.stringify(data);
    console.log(jsonData);
    var xhr = new XMLHttpRequest();
    xhr.onload = function(){
        if(xhr.readyState === 4 && xhr.status === 200){
            console.log("Successful Registration");
        }
        else if(xhr.status !== 200){
            console.log('Registration failed with status code ' + xhr.status);
        }
    };   
    xhr.open('POST','Servlet');
    xhr.setRequestHeader('Content-type','application/json');
    xhr.send(jsonData);
}
                
function addVehiclePost()
{
    let myForm = document.getElementById("vehicleForm");
    let formData = new FormData(myForm);
    const data = {};
    formData.forEach((value,key) => (data[key] = value));
    var jsonData = JSON.stringify(data);
    console.log(jsonData);
    var xhr = new XMLHttpRequest();
    xhr.onload = function(){
        if(xhr.readyState === 4 && xhr.status === 200){
            alert("Succesful Addition");
        }
        else if(xhr.status !== 200){
            alert('Registration failed with status code ' + xhr.status );
        }
    };
    xhr.open('POST','addCarServlet');
    xhr.setRequestHeader('Content-type','application/json');
    xhr.send(jsonData);
}
                
function displayVehicles()
{                    
    var xhr = new XMLHttpRequest();
    xhr.open('GET','displayVehiclesServlet');
    xhr.setRequestHeader('Content-type','application/json');
    xhr.onreadystatechange=function()
    {
        if(xhr.readyState===4 && xhr.status===200)
        {
            alert("Succesful Display Vehicles");
            var response=xhr.responseText;
            document.getElementById("").innerHTML="<br>"+"<br>"+response;
        }
        else if(xhr.status !== 200){
            alert('Display failed with status code ' + xhr.status );
        }
    };
    xhr.send();
}

 
function searchVehicles(){
    var xhr = new XMLHttpRequest();
    xhr.open('GET','search');
    xhr.setRequestHeader('Content-type','application/json');
    xhr.onreadystatechange=function()
    {
        if(xhr.readyState===4 && xhr.status===200)
        {
            alert("Available");
            var response=xhr.responseText;
            document.getElementById("genrevehicle").innerHTML="<br>"+"<br>"+response;
        }
        else if(xhr.status !== 200){
            alert('Unavailable' + xhr.status );
        }
    };
    xhr.send();
}


function popup1() {
    window.open("addVehicle.html", "Frame", "height=500, width=500")
}


function checkTheGenre() {
            // Get the selected genre
            var selectedGenre = document.getElementById("genre").value;

            // Define the URLs for each genre
            var carUrl = "addCarPage.html";
            var motorcycleUrl = "addMotorcyclePage.html";
            var ebikeUrl = "addeBikePage.html";
            var scooterUrl = "addScooterPage.html";

            // Redirect based on the selected genre
            switch (selectedGenre) {
                case "Car":
                    window.location.href = carUrl;
                    break;
                case "Motorcycle":
                    window.location.href = motorcycleUrl;
                    break;
                case "eBike":
                    window.location.href = ebikeUrl;
                    break;
                case "Scooter":
                    window.location.href = scooterUrl;
                    break;
                default:
                    // Handle default case or show an alert
                    alert("Invalid genre selected");
            }
        }
 function rdirect() {
            // Redirect to the main page
            window.location.href = "index.html";
        }

  function openPopup()
        { 
            window.location.href = "add_vehicle.html";
        }
        

