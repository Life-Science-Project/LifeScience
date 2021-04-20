import React, {useEffect, useState} from "react";
import './method.css'
import {
    BrowserRouter as Router,
    Switch,
    Route,
    NavLink,
    Redirect
} from "react-router-dom";
import GeneralInformation from "./Sections/GeneralInformation/general-information";
import Protocol from "./Sections/Protocol/protocol";
import Application from "./Sections/Application/application";
import Advantages from "./Sections/AdvantagesDisadvantages/advantages-disadvantages";
import Collaboration from "./Sections/FindCollaboration/collaboration";
import Page from "../Page/Page";
import Equipment from "./Sections/Equipment/equipment";
import Troubleshooting from "./Sections/Troubleshooting/troubleshooting";
import Education from "./Sections/Education/education";

const sectionFunctions = [
    (section) => (<GeneralInformation {...section}/>),
    (section) => (<Protocol paragraphs={section.paragraphs}/>),
    (section) => (<Equipment paragraphs={section.paragraphs}/>),
    (section) => (<Application/>),
    (section) => (<Advantages/>),
    (section) => (<Troubleshooting paragraphs={section.paragraphs}/>),
    (section) => (<Collaboration {...section}/>),
    (section) => (<Education/>)
]

const URL_API = "https://life-science-2021.herokuapp.com/api";
const URL_ARTICLES = URL_API + "/articles"
const GENERAL_INFO_LINK = "/general-information"

const buildSectionUrl = (versionId, sectionId) => URL_ARTICLES
    + "/versions/" + versionId + "/sections/" + sectionId

const Method = ({link, articleId}) => {

    const [name, setName] = useState("")
    const [sections, setSections] = useState([])

    const getJson = async (url) => {
        const response = await fetch(url);
        return await response.json();
    }

    const fetchVersionId = async () => {
        return await getJson('https://life-science-2021.herokuapp.com/api/articles/' + articleId)
    }

    const fetchSection = async (versionId, sectionId) => {
        return await getJson(buildSectionUrl(versionId, sectionId))
    }

    const toLinkPart = (str) => str.replace(/\s+/g, '-').toLowerCase();

    useEffect(() => {
        const getMethodData = async () => {
            let response;
            try {
                response = await fetchVersionId()
            } catch (e) {
                console.log(e)
                return
            }
            const pageName = response.version.name;
            const versionId = response.id
            const sectionIds = response.version.sectionsIds
            const fetchedSections = [];
            for (const sectionId of sectionIds) {
                let section;
                try {
                    section = await fetchSection(versionId, sectionId)
                } catch (e) {
                    console.log(e)
                    return
                }
                section.link = link + "/" + toLinkPart(section.name);
                fetchedSections.push(section)
            }
            setSections(fetchedSections)
            setName(pageName)
        }

        getMethodData()
    }, [])


    return (
        <Router>
            <div className="method-name">
                <h2>
                    {name}
                </h2>
            </div>
            <div className="main">
                <ul className="section-list">
                    {
                        sections.map((section) => (
                            <li className="list-item">
                                <NavLink exact activeClassName="active-section" className="section-link"
                                         to={section.link}>{section.name}</NavLink>
                            </li>
                        ))
                    }
                </ul>
                <Switch>
                    {
                        <Redirect exact from={link} to={link + GENERAL_INFO_LINK}/>
                    }
                </Switch>
                <Switch>
                    {
                        sections.map((section, index) => (
                            <Route exact={index === 0} path={section.link}>
                                {
                                    sectionFunctions[index](section)
                                }
                            </Route>
                        ))

                    }
                </Switch>
            </div>
        </Router>

    )
}


Method.defaultProps = {
    articleId: 1,
    link: "/bradford-assay",
    name: "Bradford Assay from default props",
    sections: [
        {
            link: "/bradford-assay/general",
            name: "General Information",
            content: "<div>Method for quantify the protein content in sample. This method has multiple applications in experimental sciences. <a href='/chemical-basis'>Chemical basis</a> of the Bradford method (1976) is based on the absorbance shift observed in an acidic solution of dye Coomassie® Brilliant Blue G-250. When added to a solution of protein, the dye binds to the protein resulting in a color change from a reddish brown to blue.</div>",
            refs: ["Bradford MM A rapid and sensitive method for the quantitation of microgram quantities of protein utilizing the principle of protein-dye binding. // Analytical Biochemistry. 1976. № 72. С. 248-254.",
                "Pedrol, Nuria & Tamayo, Pilar. (2001). Protein Content Quantification by Bradford Method. 10.1007/0-306-48057-3_19. "]
        },
        {
            link: "/bradford-assay/protocol",
            name: "Protocol",
            content: "too hard for me right now"
        },
        {
            link: "/bradford-assay/application",
            name: "Application",
            content: ["<a href='/link1'>Quantitative analysis of proteins</a>",
                "<a href='/link2'>Some other application</a>"],
        },
        {
            link: "/bradford-assay/advantages",
            name: "Advantages and disadvantages",
            content: [
                ["by whom / parameter", "by Admin1", "by Admin2", "by Admin3"],
                ["Cost", "$1 per gram", "$1.5 per gram", ""],
                ["Time", "2 hours", "2.5 hours", "120 minutes"],
                ["Sensitivity", "High", "", "Very High"]
            ]
        },
        {
            link: "/bradford-assay/collaboration",
            name: "Find collaboration",
            content: {}
        }

    ]
}


export default Method