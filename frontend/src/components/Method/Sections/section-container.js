import React, {useEffect} from "react";
import {useRouteMatch, withRouter} from "react-router-dom";
import {useDispatch, useSelector} from "react-redux";
import {fetchContents, clearContents} from "../../../redux/section-reducer";
import Section from "./section";
import Preloader from "../../common/Preloader/preloader";

const SectionContainer = (props) => {

    const dispatch = useDispatch()
    const match = useRouteMatch()

    const name = useSelector(state => state.section.name);
    const contents = useSelector(state => state.section.contents);
    const isReceived = useSelector(state => state.section.isReceived)

    useEffect(() => {
        getContents()

        return () => {
            dispatch(clearContents())
        }
    }, [props.versionId, match.params])

    const getContents = () => {
        const versionId = props.versionId;
        const sectionId = match.params.sectionId;
        dispatch(fetchContents(versionId, sectionId))
    }

    if (!isReceived) return <Preloader/>
    return (
        <Section contents={contents} name={name}/>
    )



}

export default withRouter(SectionContainer)