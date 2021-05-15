import React from "react";
import Error from "../Error/error";

class ErrorBoundary extends React.Component {
    constructor(props) {
        super(props);
        this.state = { hasError: false };
    }

    static getDerivedStateFromError(error) {
        return { hasError: true };
    }

    componentDidCatch(error, errorInfo) {
        // TODO добавить логирование ошибок для исправления
    }

    render() {
        if (this.state.hasError) {
            return <Error error={{message: "Sorry, something goes wrong, we don't know why, but we will find out.", status: 400}}/>
        }
        return this.props.children;
    }
}

export default ErrorBoundary;
