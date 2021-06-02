import React, {Component} from 'react';
import BackendService from '../services/BackendService';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import {faChevronLeft, faSave} from '@fortawesome/fontawesome-free-solid'
import {alertActions} from "../utils/Rdx"
import {connect} from "react-redux"
import {Form} from "react-bootstrap"

class PaintingComponent extends Component {
    constructor(props) {
        super(props)
        this.state = {
            id: this.props.match.params.id,
            name: '',
            year: '',
            artist: '',
            museum: '',
            hidden: false,
        }

        this.onSubmit = this.onSubmit.bind(this)
        this.handleChange = this.handleChange.bind(this)
    }

    handleChange({target}) {
        this.setState({[target.name]: target.value});
    };

    onSubmit(event) {
        event.preventDefault();
        event.stopPropagation();
        let err = null;
        if (!this.state.name) {
            err = "Название картины должно быть указано"
        }
        if (!this.state.year) {
            err = "Год создания картины должен быть указан"
        }
        if (!this.state.artist) {
            err = "Автор картины должен быть указан"
        }
        if (!this.state.museum) {
            err = "Музей, в котором находится данная картина, должен быть указан"
        }
        if (err) {
            this.props.dispatch(alertActions.error(err))
        }
        let painting = {id: this.state.id, name: this.state.name,
            year: this.state.year, artist: this.state.artist.name, museum: this.state.museum.name}
        if (parseInt(painting.id) === -1) {
            BackendService.createPainting(painting)
                .then(() => this.props.history.push('/paintings'))
                .catch(() => {
                })
        } else {
            BackendService.updatePainting(painting)
                .then(() => this.props.history.push('/paintings'))
                .catch(() => {
                })
        }
    }

    componentDidMount() {
        if (parseInt(this.state.id) !== -1) {
            BackendService.retrievePainting(this.state.id)
                .then((resp) => {
                    this.setState({
                        name: resp.data.name,
                        year: resp.data.year,
                        artist: resp.data.artist,
                        museum: resp.data.museum
                    })
                })
                .catch(() => this.setState({hidden: true}))
        }
    }

    render() {
        if (this.state.hidden)
            return null;
        return (
            <div className="m-4">
                <div className=" row my-2 mr-0">
                    <h3>Картина</h3>
                    <button className="btn btn-outline-secondary ml-auto"
                            onClick={() => this.props.history.push('/paintings')}
                    ><FontAwesomeIcon
                        icon={faChevronLeft}/>{' '}Назад</button>
                </div>
                <Form onSubmit={this.onSubmit}>
                    <Form.Group>
                        <Form.Label>Название картины</Form.Label>
                        <Form. Control
                               type="text"
                               placeholder="Введите название картины"
                               onChange={this.handleChange}
                               value={this.state.name}
                               name="name"
                               autoComplete="off"
                        />
                        <Form.Label>Год создания</Form.Label>
                        <Form. Control
                               type="text"
                               placeholder="Введите год создания картины"
                               onChange={this.handleChange}
                               value={this.state.year}
                               name="year"
                               autoComplete="off"
                        />
                        <Form.Label>Автор картины</Form.Label>
                        <Form. Control
                               type="text"
                               placeholder="Введите автора картины"
                               onChange={this.handleChange}
                               value={this.state.artist.name}
                               name="artist"
                               autoComplete="off"
                        />
                        <Form.Label>Музей, в котором находится картина</Form.Label>
                        <Form. Control
                               type="text"
                               placeholder="Введите название музея, в котором находится картина"
                               onChange={this.handleChange}
                               value={this.state.museum.name}
                               name="museum"
                               autoComplete="off"
                        />
                    </Form.Group>
                    <button
                        className="btn btn-outline-secondary"
                        type="submit"><FontAwesomeIcon
                        icon={faSave}/>{' '}Сохранить
                    </button>
                </Form>
            </div>
        )
    }
}

export default connect()(PaintingComponent);