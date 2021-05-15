import React, {useEffect, useState} from "react";
import {useHistory, useRouteMatch, withRouter} from "react-router-dom";
import {useDispatch, useSelector} from 'react-redux';
import {fetchSections, clearSections, passSectionFunc, clearSectionFunction} from "../../redux/method-reducer";
import Method from "./method";
import Preloader from "../common/Preloader/preloader";
import AddButton from "./AddButton/addButton";
import {
    getSectionsForMain,
    getSectionsForProtocol
} from "../../utils/sections";
import {METHOD_URL, PROTOCOLS} from "../../constants";


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
    const isSectionSelected = useSelector(state => state.method.isSectionSelected)
    const protocolName = useSelector(state => state.method.protocolName)
    const articleVersionId = useSelector(state => state.method.articleVersionId)

    let isClearFunction = true

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
            console.log("cleaning: " + isClearFunction)
            dispatch(clearSections())
            if (isClearFunction) {
                dispatch(clearSectionFunction())
            }
        }
    }, [match.params])

    const getAddButton = () => {
        return isAuthorized && <AddButton versionId={versionId}/>
    }

    const getBackToProtocols = () => {
        dispatch(passSectionFunc(section => section.name === PROTOCOLS))
        isClearFunction = false
        console.log("isClear: " + isClearFunction)
        history.push(`${METHOD_URL}/${articleVersionId}`)
    }

    const getNewProtocolButton = () => {
        return isAuthorized &&
            <button onClick={addProtocol} type={"button"}>
                Add protocol
            </button>
    }

    const getBackToProtocolsButton = () => {
        return !isMainPage &&
            <button onClick={getBackToProtocols} type={"button"}>
                Back to protocols
            </button>
    }


    if (!isReceived) return <Preloader/>
    return (
        <Method name={name + (protocolName ? `, ${protocolName}` : "")}
                sections={(isMainPage) ? getSectionsForMain(sections) : getSectionsForProtocol(sections)}
                versionId={versionId}
                addButton={getAddButton()}
                isSectionSelected={isSectionSelected}
                newProtocolButton={getNewProtocolButton()}
                backToProtocolsButton={getBackToProtocolsButton()}/>
    );

}

export default withRouter(MethodContainer)