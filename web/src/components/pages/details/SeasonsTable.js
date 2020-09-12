import React, { useState } from "react";
import {
  Button,
  List,
  ListItem,
  ListItemText,
  Collapse,
  Divider,
  Tab,
  Tabs,
} from "@material-ui/core";
import "../../../styles/details.css";

const ChapterItem = ({ chapter, index, playHandler }) => {
  const [open, setOpen] = useState(false);

  const handleClick = () => {
    setOpen(!open);
  };
  return (
    <div>
      <ListItem button onClick={handleClick}>
        <ListItemText primary={chapter.title} />
      </ListItem>
      <Collapse
        in={open}
        timeout="auto"
        unmountOnExit
        className="chapter-info-container"
      >
        <List>
          <ListItem alignItems="flex-start" key={`descKeyCh-${index}`}>
            <ListItemText
              primary={
                <>
                  {chapter.description} <br />
                  <Button
                    variant="contained"
                    onClick={() => playHandler(chapter.video)}
                  >
                    Play
                  </Button>
                </>
              }
            />
          </ListItem>
        </List>
      </Collapse>
      <Divider variant="middle" component="li" />
    </div>
  );
};

const TabPanel = (props) => {
  const { children, value, index } = props;

  return (
    <div
      hidden={value !== index}
      id={`scrollable-auto-tabpanel-${index}`}
      aria-labelledby={`scrollable-auto-tab-${index}`}
    >
      {value === index && <>{children}</>}
    </div>
  );
};

const SeasonsTable = ({ seasons, playHandler }) => {
  const [value, setValue] = useState(0);

  return (
    <div className="seasons-container">
      <Tabs
        value={value}
        onChange={(_, newValue) => setValue(newValue)}
        indicatorColor="secondary"
        textColor="primary"
        variant="scrollable"
        scrollButtons="auto"
      >
        {seasons.map((season, index) => (
          <Tab label={season.title} key={`{tabSeason-${index}`} />
        ))}
      </Tabs>
      {seasons.map((season, index) => (
        <TabPanel value={value} index={index} key={`paneSeasonKey-${index}`}>
          <List className="chapters-container">
            {season.chapters
              ? season.chapters.map((item, chIndx) => (
                  <ChapterItem
                    key={`chapterItem-${chIndx}`}
                    index={chIndx}
                    chapter={item}
                    playHandler={playHandler}
                  />
                ))
              : null}
          </List>
        </TabPanel>
      ))}
    </div>
  );
};

export default SeasonsTable;
