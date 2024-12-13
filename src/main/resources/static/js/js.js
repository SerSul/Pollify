document.addEventListener('DOMContentLoaded', function () {
    const codeInput = document.getElementById('uri');
    const openButton = document.getElementById('openQuestionnaire');
    const editButton = document.getElementById('editQuestionnaire');
    const questionnaireCreate = document.getElementById('createQuestionnaire');


    questionnaireCreate.addEventListener('click', function () {
        window.location.href = '/questionnaire/create';
    });

    editButton.addEventListener('click', function () {
        const uri = prompt("Введите код анкеты:");
        const token = prompt("Введите токен:");
        document.getElementById('uriInput').value = uri;
        document.getElementById('tokenInput').value = token;

        document.getElementById('editQuestionnaireForm').submit();

    });


    openButton.addEventListener('keypress', function (e) {
        if (e.key === 'Enter') {
            openButton.click();
        }
    });
});


window.onload = function () {
    const errorMessage = document.querySelector('.error-message');

    if (errorMessage && errorMessage.style.display !== 'none') {
        errorMessage.style.display = 'block';
        setTimeout(function () {
            errorMessage.style.display = 'none';
        }, 5000);
    }
};
