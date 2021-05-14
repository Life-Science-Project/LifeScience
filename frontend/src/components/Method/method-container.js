import React, {useEffect, useState} from "react";
import {useRouteMatch, withRouter} from "react-router-dom";
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

    const name = useSelector(state => state.method.name)
    const sections = useSelector(state => state.method.sections)
    const isReceived = useSelector(state => state.method.isReceived)
    const isAuthorized = useSelector(state => state.auth.isAuthorized)
    const isMainPage = useSelector(state => state.method.isMainPage)
    const passedSectionId = useSelector(state => state.method.passedSectionId)

    const getSections = () => {
        const id = match.params.versionId;
        setVersionId(id)
        dispatch(fetchSections(id))
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


    if (!isReceived) return <Preloader/>
    return (
        <div>
            <Method name={name}
                    sections={(isMainPage) ? getSectionsForMain(sections) : getSectionsForProtocol(sections)}
                    versionId={versionId}
                    addButton={getAddButton()}
                    passedSectionId={passedSectionId}/>
        </div>
    );

}

export default withRouter(MethodContainer)