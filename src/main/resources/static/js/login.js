$(document).ready(function() {
    var url = window.location.href
    if(url.indexOf("#") != -1) {
        var anchor = url.substring(url.indexOf("#")+1)
        
        if(anchor === "register") {
            $(".nav-tabs a[href='#register']").tab("show")
        }
    }
})