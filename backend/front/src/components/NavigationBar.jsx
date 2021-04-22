import React from "react";
import { Navbar, Nav, NavDropdown } from "react-bootstrap"
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome"
import {faHome} from '@fortawesome/fontawesome-free-solid'
import { withRouter } from "react-router-dom";


// этот класс наследуется от React.Component
class NavigationBar extends React.Component {

    // props - Это параметры которые можно при желании передать через атрибуты элемента
    constructor(props) {
        super(props);
    }


    // отображает компонент в окне браузера
    render() {
        return (
            <Navbar bg="light" expand="lg">
                <Navbar.Brand><FontAwesomeIcon icon={faHome}/>{' '}myRPO</Navbar.Brand>
                <Navbar.Toggle aria-controls="basic-navbar-nav" />
                <Navbar.Collapse id="basic-navbar-nav">
                    <Nav className="mr-auto">
                        <Nav.Link href="/home">Home</Nav.Link>
                        <Nav.Link onLink={() => {this.props.history.push("/home")}}>Another home</Nav.Link>
                    </Nav>
                </Navbar.Collapse>
            </Navbar>
        );
    }
}

export default withRouter(NavigationBar);