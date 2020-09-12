import React from "react";
import Modal from '@material-ui/core/Modal';
import ReactPlayer from "react-player";
import "../../../styles/details.css"

const PlayerModal = ({isShowing, hide, videoUrl, onStartPlaying}) => (
    <Modal open={isShowing} onClose={hide} className='player-modal'>
      <ReactPlayer url={videoUrl} controls width='70%' height='70%' onStart={onStartPlaying}/>
    </Modal>
  );

export default PlayerModal;

