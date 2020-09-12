import React from "react";
import { Link } from "react-router-dom";

function Poster({ content, height }) {
  const { title, poster, id } = content;

  return (
    <Link to={`/details/${id}`} key={`link-key-to-${id}`}>
      <img src={poster} height={height} alt={title} />
    </Link>
  );
}

export default Poster;
