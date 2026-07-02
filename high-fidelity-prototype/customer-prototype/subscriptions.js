const SUBSCRIPTIONS_KEY = 'mealprep-subscriptions';

function getSubscriptions() {
    try {
        return JSON.parse(localStorage.getItem(SUBSCRIPTIONS_KEY)) || [];
    } catch {
        return [];
    }
}

function saveSubscriptions(ids) {
    localStorage.setItem(SUBSCRIPTIONS_KEY, JSON.stringify(ids));
}

function initSubscribeButtons({ hideUnsubscribed = false } = {}) {
    const subscribed = new Set(getSubscriptions());
    const cards = document.querySelectorAll('.service-card[data-id]');
    const noResults = document.querySelector('.no-subscriptions');

    function updateVisibility() {
        if (!hideUnsubscribed) return;
        let visibleCount = 0;
        cards.forEach(card => {
            const isSubscribed = subscribed.has(card.dataset.id);
            card.hidden = !isSubscribed;
            if (isSubscribed) visibleCount++;
        });
        if (noResults) noResults.hidden = visibleCount > 0;
    }

    cards.forEach(card => {
        const btn = card.querySelector('.subscribe-btn');
        const id = card.dataset.id;
        const isSubscribed = subscribed.has(id);
        btn.classList.toggle('subscribed', isSubscribed);
        btn.textContent = isSubscribed ? 'Subscribed' : 'Subscribe';

        btn.addEventListener('click', () => {
            if (subscribed.has(id)) {
                subscribed.delete(id);
            } else {
                subscribed.add(id);
            }
            saveSubscriptions([...subscribed]);

            const nowSubscribed = subscribed.has(id);
            btn.classList.toggle('subscribed', nowSubscribed);
            btn.textContent = nowSubscribed ? 'Subscribed' : 'Subscribe';
            updateVisibility();
        });
    });

    updateVisibility();
}
