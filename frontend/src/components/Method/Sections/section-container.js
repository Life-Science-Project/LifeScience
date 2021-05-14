import React, {useEffect} from "react";
import {withRouter} from "react-router-dom";
import {useDispatch, useSelector} from "react-redux";
import {fetchContents, clearContents} from "../../../redux/section-reducer";
import Section from "./section";
import Preloader from "../../common/Preloader/preloader";

const SectionContainer = (props) => {

    const {versionId, section} = props

    const dispatch = useDispatch()

    const contents = useSelector(state => state.section.contents);
    const isReceived = useSelector(state => state.section.isReceived)
    const articleId = useSelector(state => state.method.articleId)

    const hasContents = section.id

    useEffect(() => {
        getContents()

        return () => {
            dispatch(clearContents())
        }
    }, [props])

    const getContents = () => {
        if (hasContents) dispatch(fetchContents(versionId, section.id))
    }

    if (!isReceived && hasContents) return <Preloader/>
    return (
        <Section contents={contents} name={section.name} articleId={articleId}/>
    )

}

export default withRouter(SectionContainer)