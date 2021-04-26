import React from "react";
import TextAnimation from 'react-animate-text';

class Home extends React.Component {

    constructor(props) {
        super(props);
    }

    render() {
        return (

            <div className="mt-5">
                <h2>
                    <TextAnimation animation="type" charInterval="100">
                        RPO Art Frontend
                    </TextAnimation>
                </h2>
            </div>

        );
    }
}

export default Home;