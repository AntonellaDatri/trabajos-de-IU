import React, { useState, useEffect } from "react";
import { useHistory } from "react-router-dom";
import { Typography, Button, Grid, LinearProgress } from "@material-ui/core";
import SeasonsTable from "./SeasonsTable";
import Api from "../../../controllers/ContentController";
import PlayerModal from "./PlayerModal";
import Navbar from "../../../navBar/Navbar";
import MediaCarrousel from "../../../mediaCarrousel/MediaCarrousel";
import NotFound from "../../../search/not-found-image.jpg";
import "../../../styles/details.css";
import AddFavorite from "./AddFavorite"
 
const useModal = () => {
  const [isShowing, setIsShowing] = useState(false);

  function toggle() {
    setIsShowing(!isShowing);
  }

  return {
    isShowing,
    toggle,
  };
};

const Details = ({
  match: {
    params: { id },
  },
}) => {
  const { isShowing, toggle } = useModal();
  const [isLoading, setIsLoading] = useState(true);
  const [title, setTitle] = useState("");
  const [description, setDescription] = useState("");
  const [videoUrl, setVideoUrl] = useState("");
  const [poster, setPoster] = useState("");
  const [relatedContent, setRelatedContent] = useState([]);
  const [categories, setCategories] = useState([]);
  const [duration, setDuration] = useState("");
  const [seasons, setSeasons] = useState([]);
  const history = useHistory();

  useEffect(() => {
    setIsLoading(true);
    const verifyPoster = (_poster) =>
      Api.verifyPoster(_poster)
        .then((response) => {
          if (response.data !== "<h1>File not Found</h1>") {
            setPoster(_poster);
          } else {
            setPoster(NotFound);
          }
        })
        .catch((error) => setPoster(NotFound));

    Api.getImages(sessionStorage.getItem("accessToken"), id)
      .then((response) => {
        setTitle(response.data.title);
        setDescription(response.data.desciption);
        verifyPoster(response.data.poster);
        setCategories(response.data.categories.map((c) => c.name));
        setRelatedContent(response.data.relatedContent);
        if (id.startsWith("mov")) { //Medio rancio, para que no explote... Supongo que hay que corregirlo en algún momento..
          setVideoUrl(response.data.viedeo);
          setDuration(response.data.duration);
          setSeasons([]);
        }
        else {
          setSeasons(response.data.season);
        }
      })
      .catch((error) => {
        console.log("Error - ", error); 
        history.push("/detailsNotFound");
      })
      .finally(() => setIsLoading(false));

  }, [id, history]);

  const handlePlay = (videoUrl) => {
    setVideoUrl(videoUrl);
    toggle();
  };

  const onStartPlaying = () => {
    Api.addLastSeen(sessionStorage.getItem("accessToken"), id);
  }

 
  return isLoading ? (
    <LinearProgress variant="indeterminate" />
  ) : (
    <>
      <Navbar />
      <Grid container spacing={3} className="details-grid">
        <Grid item>
          <img height="400px" width="300px" alt="PosterImg" src={poster} />
          <figcaption>{categories.join(", ")}</figcaption>
          <AddFavorite contentID = {id}/>
        </Grid>
        <Grid item xs container direction="column" spacing={3} zeroMinWidth>
          <Grid item xs>
            <Typography gutterBottom variant="h3">
              {title}
            </Typography>
            {id.startsWith("mov_") && (
              <Typography variant="overline" color="textSecondary">
                {`Duration: ${duration} minutes`}
              </Typography>
            )}
            <Typography variant="body1" paragraph>
              {description}
            </Typography>
          </Grid>
          <Grid item xs>
            {id.startsWith("mov_") ? (
              <Button
                variant="contained"
                size="large"
                style={{ width: "20em" }}
                onClick={() => handlePlay(videoUrl)}
              >
                Play
              </Button>
            ) : (
              <SeasonsTable
                seasons={seasons}
                playHandler={handlePlay}
                key={`season-table-key-${id}`}
              />
            )}
          </Grid>
        </Grid>
      </Grid>
      <h5>Related Content</h5>
      <MediaCarrousel content={relatedContent} />
      <PlayerModal hide={toggle} isShowing={isShowing} videoUrl={videoUrl} onStartPlaying={onStartPlaying}/>
    </>
  );
};

export default Details;
