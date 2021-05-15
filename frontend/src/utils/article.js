export const createArticle = (categoryId, name, sections) => {
    return {
        articleDTO: {
            categoryId: categoryId
        },
        name: name,
        sections: sections
    }
}

export const createReview = (resolution, comment) => {
    return {
        comment: comment,
        resolution: resolution
    }
}

export const createRequest = (destination) => {
    return {
        destination: destination
    }
}
