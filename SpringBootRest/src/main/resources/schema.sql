drop table tracks if exists;
drop table recordings if exists;
drop table musiccategories if exists;
drop table recordingartists if exists;

create table MusicCategories
(
    MusicCategoryID integer not null primary key AUTO_INCREMENT,
    MusicCategory varchar(20) not null
);

create table RecordingArtists
(RecordingArtistID integer not null primary key AUTO_INCREMENT,
RecordingArtistName varchar(36),
Notes varchar(200));

create table Recordings
(RecordingID integer not null primary key AUTO_INCREMENT,
RecordingTitle varchar(50),
RecordingArtistID integer,
MusicCategoryID integer,
RecordingLabel varchar(36),
Format varchar(20),
NumberofTracks smallint,
Notes varchar(200),
foreign key (RecordingArtistID)
references RecordingArtists(RecordingArtistID),
foreign key (MusicCategoryID)
references MusicCategories(MusicCategoryID));

create table Tracks
(TrackID integer not null primary key AUTO_INCREMENT,
TrackNumber smallint,
TrackTitle varchar(100),
TrackLength varchar(10),
RecordingID integer,
foreign key (RecordingID)
references Recordings(RecordingID));