document.addEventListener('DOMContentLoaded', () => {
    const forms = {
        recipe: document.getElementById('recipe-form'),
        mealplan: document.getElementById('mealplan-form'),
        mealkit: document.getElementById('mealkit-form')
    };

    document.querySelectorAll('input[name="uploadType"]').forEach(radio => {
        radio.addEventListener('change', () => {
            for (const [type, form] of Object.entries(forms)) {
                form.hidden = type !== radio.value;
            }
        });
    });
});
