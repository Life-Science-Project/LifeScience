import {AUTO_SECTION_TITLES, INFO_SECTION_TITLES, PROTOCOLS, SECTION_TITLES} from "../constants";

export const getSectionsForShow = (sections) => {
    return sortSectionsBy(sections, SECTION_TITLES);
}

//todo rename
export const getSectionsForPreview = (sections) => {
    const sortedSections = sortSectionsBy(sections, INFO_SECTION_TITLES);
    pushAutoSections(sortedSections)
    return sortedSections
}

const pushAutoSections = (sections) => {
    for (const title of AUTO_SECTION_TITLES) {
        sections.push({
            name: title
        })
    }
}

export const getSectionsForProtocol = (sections) => {
    const sortedSections = sortSectionsBy(sections, INFO_SECTION_TITLES);
    pushAutoSections(sortedSections)
    return sortedSections
}

export const getSectionsForMain = (sections) => {
    const sortedSections = sortSectionsBy(sections, INFO_SECTION_TITLES);
    sortedSections.splice(1, 0, {name: PROTOCOLS})
    pushAutoSections(sortedSections)
    return sortedSections
}


const sortSectionsBy = (sections, example) => {
    const sortedSections = [];
    for (const title of example) {
        for (const section of sections) {
            if (section.name === title) {
                sortedSections.push(section)
            }
        }
    }
    return sortedSections;
}