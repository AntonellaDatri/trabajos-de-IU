import React, { useState, useEffect } from "react";
import Poster from "../components/content/Poster";
import Api from "../controllers/ContentController";
import NotFound from "../search/not-found-image.jpg";
import HorizontalScroller from "react-horizontal-scroll-container";

function MediaCarrousel({ content }) {
  const [contents, setContents] = useState([]);
  const authorization = sessionStorage.getItem("accessToken");

  useEffect(() => {
    content.forEach((content) => {
      getImages(content.id);
    });
  }, [content]);

  const getImages = (id) => {
    Api.getImages(authorization, id)
      .then((response) => {
        verifyImage(response.data);
      })
      .catch((error) => console.log(error.response?.data.title));
  };

  const verifyImage = (content) => {
    Api.verifyPoster(content.poster)
      .then((response) => {
        if (response.data !== "<h1>File not Found</h1>") {
          setContentsWithPoster(content.poster, content);
        } else {
          setContentsWithPoster(NotFound, content);
        }
      })
      .catch((error) => setContentsWithPoster(NotFound, content));
  };

  const setContentsWithPoster = (p, content) => {
    setContents((prevContent) =>
      prevContent.concat({
        poster: p,
        id: content.id,
        title: content.title,
      })
    );
  };

  return (
    <div className="media-carrousel">
      <HorizontalScroller>
        {contents.map((c) => (
          <Poster content={c} height='250' key={`poster-media-${c.id}`} />
        ))}
      </HorizontalScroller>
    </div>
  );
}
export default MediaCarrousel;
