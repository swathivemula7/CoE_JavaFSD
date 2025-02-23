async function fetchNews() {
    try {
        const response = await fetch('news.json');
        const data = await response.json();
        displayFeaturedNews(data.articles.slice(0, 3)); 
        startSlider();
    } catch (error) {
        console.error('Error fetching news:', error);
    }
}

function displayFeaturedNews(articles) {
    const slider = document.getElementById('news-slider');
    slider.innerHTML = articles.map(article => `
        <div class="slide">
            <div class="slide-content">
                <h3><a href="${article.url}" target="_blank">${article.title}</a></h3>
                <p>${article.description}</p>
                <div class="actions">
                    <button class="like-btn" onclick="toggleLike(this)">🤍</button>
                    <button class="comment-btn" onclick="toggleCommentBox(this)">💬 Comment</button>
                </div>
                <div class="comment-section" style="display: none;">
                    <textarea class="comment-box" placeholder="Write a comment..."></textarea>
                    <button class="post-btn" onclick="postComment(this)">Post</button>
                </div>
            </div>
            <img src="${article.image}" alt="${article.title}">
        </div>
    `).join('');
}

function filterNews(category) {
    fetch('news.json')
        .then(response => response.json())
        .then(data => {
            const filteredNews = data.articles.filter(article => article.category === category).slice(0, 3);
            const newsContainer = document.getElementById('news-container');
            newsContainer.innerHTML = filteredNews.map(article => `
                <div class="news-card">
                    <img src="${article.image}" alt="${article.title}">
                    <h3><a href="${article.url}" target="_blank">${article.title}</a></h3>
                    <p>${article.description}</p>
                    <div class="actions">
                        <button class="like-btn" onclick="toggleLike(this)">🤍</button>
                        <button class="comment-btn" onclick="toggleCommentBox(this)">💬 Comment</button>
                    </div>
                    <div class="comment-section" style="display: none;">
                        <textarea class="comment-box" placeholder="Write a comment..."></textarea>
                        <button class="post-btn" onclick="postComment(this)">Post</button>
                    </div>
                </div>
            `).join('');
        });
}

let currentSlide = 0;

function startSlider() {
    const slides = document.querySelectorAll('.slide');
    const totalSlides = slides.length;

    setInterval(() => {
        slides.forEach((slide, index) => {
            slide.style.transform = `translateX(-${currentSlide * 100}%)`;
        });
        currentSlide = (currentSlide + 1) % totalSlides;
    }, 3000); 
}


function toggleLike(btn) {
    btn.textContent = btn.textContent === '🤍' ? '❤️' : '🤍';
}


function toggleCommentBox(btn) {
    const commentSection = btn.parentElement.nextElementSibling;
    commentSection.style.display = commentSection.style.display === 'none' ? 'block' : 'none';
}

function postComment(btn) {
    const commentBox = btn.previousElementSibling;
    const comment = commentBox.value.trim();
    if (comment) {
        console.log('Posted comment:', comment);
        commentBox.value = ''; 
    } else {
        alert('Please write a comment before posting.');
    }
}

fetchNews();
