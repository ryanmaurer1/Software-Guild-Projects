$('#newType').click(function (event) {
    console.log("button was clicked");
    $('#selectType').hide();
    $('#addNewType').show();

});

$('#cancelAddNewType').click(function (event) {
    console.log("button was clicked");
    $('#selectType').show();
    $('#addNewType').hide();

});

$('#newBrand').click(function (event) {
    console.log("button was clicked");
    $('#selectBrand').hide();
    $('#addNewBrand').show();
});

$('#cancelAddNewBrand').click(function (event) {
    console.log("button was clicked");
    $('#selectBrand').show();
    $('#addNewBrand').hide();
});
