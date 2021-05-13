import React, {useEffect, useState} from "react";
import {useHistory, useRouteMatch, withRouter} from "react-router-dom";
import {useDispatch, useSelector} from 'react-redux';
import {fetchSections, clearSections} from "../../redux/method-reducer";
import Method from "./method";
import Preloader from "../common/Preloader/preloader";
import AddButton from "./AddButton/addButton";


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
            <button onClick={addProtocol} type={"button"} className={"btn btn-primary"}>
                Add protocol
            </button>
    }


    if (!isReceived) return <Preloader/>
    return (
        <div>
            <Method name={name} sections={sections} versionId={versionId} addButton={getAddButton()}
                    newProtocolButton={getNewProtocolButton()}/>
        </div>
    );

}

export default withRouter(MethodContainer)