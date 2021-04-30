import React from "react";
import ava from "../../../../../../logos/photo.jpeg";
import PropTypes from "prop-types";

const UserAvatar = (props) => {
    return(
        <div className="user_photo">
            <img src={props.avatarSource} alt="user avatar"/>
        </div>
    );
}

UserAvatar.propTypes = {
    avatarSource: PropTypes.object
}

UserAvatar.defaultProps = {
    avatarSource: ava
}

export default UserAvatar;

