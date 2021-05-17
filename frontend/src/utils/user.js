export const getOrganisationsNames = (string) => {
    return string.split(",").map(x => x.trim())
}

export const getOrganisationsLine = (organisations) => {
    return organisations.join(", ");
}
