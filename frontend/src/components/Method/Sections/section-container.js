import React, {useEffect, useState} from "react";
import {useRouteMatch, withRouter} from "react-router-dom";
import {useDispatch, useSelector} from "react-redux";
import {fetchContents} from "../../../redux/section-reducer";
import Preloader from "../../common/Preloader/preloader";
import Section from "./section";

const SectionContainer = (props) => {

    const dispatch = useDispatch()
    const match = useRouteMatch()

    // const [name, setName] = useState("");
    // const [contents, setContents] = useState([]);
    // const [isReceived, setIsReceived] = useState(false);

    useEffect(() => {
        getContents()
    }, [props.versionId, match.params])

    const getContents = () => {
        const versionId = props.versionId;
        const sectionId = match.params.sectionId;
        dispatch(fetchContents(versionId, sectionId))
    }

    const name = useSelector(state => state.section.name);
    const contents = useSelector(state => state.section.contents);
    const isReceived = useSelector(state => state.section.isReceived);

    if (!isReceived) return <Preloader/>
    return (
        <Section contents={contents} name={name}/>
    )



}

export default withRouter(SectionContainer)