document.getElementById('crawlForm').addEventListener('submit', function(event) {
    event.preventDefault();
    var url = document.getElementById('crawlUrl').value;
    fetch(`/api/images/crawl?url=${encodeURIComponent(url)}`)
        .then(response => response.json())
        .then(imageUrls => {
            displayImages(imageUrls);
        })
        .catch(error => {
            console.error('Error during image crawling:', error);
        });
});

function displayImages(imageUrls) {
    const imagesListDiv = document.getElementById('imagesList');
    imagesListDiv.classList.add("image-list")
    imagesListDiv.innerHTML = ''; // Clear the list

    imageUrls.forEach((imageUrl, index) => {
        const checkboxId = `image-${index}`;
        imagesListDiv.innerHTML += `
            <div class = "image-box">
                <input type="checkbox" id="${checkboxId}" name="selectedImages" value="${imageUrl}">
                <label for="${checkboxId}">${imageUrl}</label><br>
                <img src="${imageUrl}" alt="Image at ${imageUrl}" class = "image"><br>
            </div>
        `;
    });

    if (imageUrls.length > 0) {
        document.getElementById('saveImagesForm').style.display = 'block';
    }
}

document.getElementById('saveImagesForm').addEventListener('submit', function(event) {
    event.preventDefault();
    var selectedImages = document.querySelectorAll('input[name="selectedImages"]:checked');
    var imageUrlsToSave = Array.from(selectedImages).map(checkbox => checkbox.value);

    fetch('/api/images/save', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(imageUrlsToSave),
    })
    .then(response => {
        if (response.ok) {
            alert('Images saved to database successfully!');
        } else {
            alert('Failed to save images to the database.');
        }
    })
    .catch(error => {
        console.error('Error during image saving:', error);
    });
});
