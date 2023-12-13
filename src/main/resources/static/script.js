$(document).ready(function () {
    $('.only-number').on('input', function() {
        $(this).val($(this).val().replace(/[^0-9.]/g, ''));
        if ($(this).val().split('.').length > 2) {
            let valueArray = $(this).val().split('.');
            valueArray.pop();
            $(this).val(valueArray.join('.'));
        }
    });
    $('.only-number-without-dot').on('input', function() {
        $(this).val($(this).val().replace(/\D/g, ''));
    });
})
