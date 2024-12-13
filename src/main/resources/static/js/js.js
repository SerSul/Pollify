document.addEventListener('DOMContentLoaded', function () {
    const codeInput = document.getElementById('questionnaireCode');
    const openButton = document.getElementById('openQuestionnaire');
    const editButton = document.getElementById('editQuestionnaire');
    const questionnaireCreate = document.getElementById('createQuestionnaire');


    questionnaireCreate.addEventListener('click', function () {
        window.location.href = '/questionnaire/create';
    });

    openButton.addEventListener('click', function () {
        const code = codeInput.value.trim();
        if (code) {
            window.location.href = '/questionnaire/load/' + code;
        } else {
            window.location.href = '/home';
        }
    });

    editButton.addEventListener('click', function () {
        const uri = codeInput.value.trim();
        const token = prompt("Введите токен:");
        document.getElementById('uriInput').value = uri;
        document.getElementById('tokenInput').value = token;

        document.getElementById('editQuestionnaireForm').submit();

    });


    codeInput.addEventListener('keypress', function (e) {
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