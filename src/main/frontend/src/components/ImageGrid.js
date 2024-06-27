import React from 'react';
import '../css/ImageGrid.css';

const ImageGrid = ({ images }) => {
    return (
        <div className="image-grid">
            {images.filter(image => image.urlSmallSize).map((image, index) => (
                <img key={index} src={image.urlSmallSize} alt={image.description} className="grid-image" />
            ))}
        </div>
    );
};

export default ImageGrid;