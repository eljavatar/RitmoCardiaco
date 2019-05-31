function handleSubmit(args, dialog) {
    var jqDialog = jQuery('#' + dialog);
    if (args.validationFailed) {
        jqDialog.effect('shake', {times: 4}, 100);
    } else {
        PF(dialog).hide();
    }
}

function applyFeriados(date) {
    var day = date.getDay();
    //return [(day != 0 && day != 6), 'feriados'];
    if (day == 0 || day == 6) {
        return [true, 'feriados'];
    }
    return [true, ''];
    //return [(day != 0 && day != 6), 'feriados'];
}


