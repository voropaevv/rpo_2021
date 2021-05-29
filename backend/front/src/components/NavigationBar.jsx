import React from "react";
import { Navbar, Nav } from "react-bootstrap"
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome"
import {faBars, faHome, faUser} from '@fortawesome/fontawesome-free-solid'
import {Link, withRouter} from "react-router-dom";
import Utils from "../utils/Utils";
import BackendService from "../services/BackendService";
import {connect} from 'react-redux';
import {userActions} from "../utils/Rdx";


class NavigationBar extends React.Component {

    // props - Это параметры которые можно при желании передать через атрибуты элемента
    constructor(props) {
        super(props);

        this.goHome = this.goHome.bind(this)
        this.logout = this.logout.bind(this)
    }

    goHome()
    {
        this.props.history.push("/home")
    }

    logout() {
        BackendService.logout().finally( () => {
            this.props.dispatch(userActions.logout())
            this.props.history.push('/login')

        })
    }

    // отображает компонент в окне браузера
    render() {
        return (
            <Navbar bg="light" expand="lg">
                <button type="button"
                        className="btn btn-outline-secondary mr-2"
                        onClick={this.props.toggleSideBar}>
                    <FontAwesomeIcon icon={faBars}/>
                </button>
                <Navbar.Brand>myRPO</Navbar.Brand>
                <Navbar.Toggle aria-controls="basic-navbar-nav" />
                <Navbar.Collapse id="basic-navbar-nav">
                    <Nav className="mr-auto">
                        <Nav.Link tag={Link} to="/home">Home</Nav.Link>
                        <Nav.Link onLink={this.goHome}>Another home</Nav.Link>
                        <Nav.Link onLink={() => {this.props.history.push("/home")}}>Yet another home</Nav.Link>
                    </Nav>
                </Navbar.Collapse>
                {this.props.user?this.props.user.login:""}
                { this.props.user &&
                    <Nav.Link onClick={this.logout}><FontAwesomeIcon icon={faUser} fixedWidth/>{' '}Выход</Nav.Link>
                }
                { !this.props.user  &&
                    <Nav.Link as={Link} to="/login"><FontAwesomeIcon icon={faUser} fixedWidth/>{' '}Вход</Nav.Link>
                }
            </Navbar>
        );
    }
}

function mapStateToProps(state) {
    const {user} = state.authentication;
    return {user};
}
export default withRouter(connect(mapStateToProps)(NavigationBar));