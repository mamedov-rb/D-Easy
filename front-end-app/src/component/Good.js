import React, {Component} from 'react'

class Good extends Component {

    constructor(props) {
        super(props);
        this.state = {image: {}}
    }

    componentDidMount() {
        this.getImage(this.props.el.images[0])
    }

    getImage = (fileName) => {
        fetch('http://localhost:8040/api/menu-position/download-file/' + fileName)
            .then(response => response.blob())
            .then(blob => this.setState({image: URL.createObjectURL(blob)}))
    }

    render() {
        return (
            <div className="ui centered card">
                <div className="image">
                    <img src={this.state.image}/>
                </div>
                <div className="content">
                    <div className="header">{this.props.el.name}</div>
                </div>
                <div className="extra content">
                    <span className="right floated">
                        <i className="percent icon"/>
                        {this.props.el.discount}
                    </span>
                    <span className="left floated">
                        <i className="ruble sign icon"/>
                        <a>{this.props.el.price}</a>
                    </span>
                </div>
            </div>
        )
    }

}

export default Good