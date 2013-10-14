var xmlHttp;

function message() {
    xmlHttp=GetXmlHttpObject();
    if (xmlHttp==null) {
        alert ("Browser does not support HTTP Request");
        return;
    } 

    var u = document.f.user.value; 
    var c = document.f.cost.value;
    var hurl = "Bank?user=" + u + "&cost=" + c;

    xmlHttp.open("GET",hurl,true);
    xmlHttp.onreadystatechange=stateChanged;
    xmlHttp.send(null);
} 


function stateChanged() {
    if (xmlHttp.readyState == 4 || xmlHttp.readyState == "complete") { 
        var firstName = xmlHttp.responseXML.getElementsByTagName("FirstName")[0].childNodes[0].nodeValue;
        var lastName = xmlHttp.responseXML.getElementsByTagName("LastName")[0].childNodes[0].nodeValue;
        var Id = xmlHttp.responseXML.getElementsByTagName("Id")[0].childNodes[0].nodeValue;
        var Balance = xmlHttp.responseXML.getElementsByTagName("Balance")[0].childNodes[0].nodeValue;
        var Status = xmlHttp.responseXML.getElementsByTagName("Status")[0].childNodes[0].nodeValue;

        Balance = parseFloat(Balance);
        Balance = Balance.toFixed(2);

        var message = "Name: " + firstName + " " + lastName + "<br>Account ID: " + Id + "<br>Account Balance: $" + Balance + "<br>";

        if(Status == "Unstable") {
            var error="<h1>You Do Not Have Sufficient Funds! </h1><br>";
        } else {
            var NewBalance = xmlHttp.responseXML.getElementsByTagName("NewBalance")[0].childNodes[0].nodeValue;
            NewBalance = parseFloat(NewBalance);
            NewBalance = NewBalance.toFixed(2);
            var error="<h1>Thank you for your purchase! Your new balance is shown below.</h1></br>New Balance: $" + NewBalance + "<br>";
        }

        document.getElementById("results").innerHTML=error + message;
    } else {
    }
} 

function GetXmlHttpObject() { 
    var objXMLHttp = null;
    if(window.XMLHttpRequest) {
        objXMLHttp = new XMLHttpRequest();
    } else if (window.ActiveXObject) {
        objXMLHttp = new ActiveXObject("Microsoft.XMLHTTP");
    }
    return objXMLHttp;
} 