import React, {useEffect, useState} from "react";
import {useHistory, useRouteMatch, withRouter} from "react-router-dom";
import {useDispatch, useSelector} from 'react-redux';
import {fetchSections, clearSections} from "../../redux/method-reducer";
import Method from "./method";
import Preloader from "../common/Preloader/preloader";
import AddButton from "./AddButton/addButton";
import {
    getSectionsForMain,
    getSectionsForProtocol
} from "../../utils/sections";


const MethodContainer = () => {

    const [versionId, setVersionId] = useState(1);
    const match = useRouteMatch()
    const dispatch = useDispatch()
    const history = useHistory();

    const name = useSelector(state => state.method.name)
    const articleId = useSelector(state => state.method.articleId)
    const sections = useSelector(state => state.method.sections)
    const isReceived = useSelector(state => state.method.isReceived)
    const isAuthorized = useSelector(state => state.auth.isAuthorized)
    const isMainPage = useSelector(state => state.method.isMainPage)
    const passedSectionId = useSelector(state => state.method.passedSectionId)
    const protocolName = useSelector(state => state.method.protocolName)

    const getSections = () => {
        const id = match.params.versionId;
        setVersionId(id)
        dispatch(fetchSections(id))
    }

    const addProtocol = () => {
        history.push(`/new-protocol/${articleId}`)
    }

    useEffect(() => {
        getSections()

        return () => {
            dispatch(clearSections())
        }
    }, [match.params])

    const getAddButton = () => {
        return isAuthorized && <AddButton versionId={versionId}/>
    }

    const getNewProtocolButton = () => {
        return isAuthorized &&
            <button onClick={addProtocol} type={"button"}>
                Add protocol
            </button>
    }


    if (!isReceived) return <Preloader/>
    return (
        <Method name={name + (protocolName ? `, ${protocolName}` : "")}
                sections={(isMainPage) ? getSectionsForMain(sections) : getSectionsForProtocol(sections)}
                versionId={versionId}
                addButton={getAddButton()}
                passedSectionId={passedSectionId}
                newProtocolButton={getNewProtocolButton()}/>
    );

}

export default withRouter(MethodContainer)