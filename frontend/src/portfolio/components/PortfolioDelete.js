import React from 'react';
import { connect } from 'react-redux';
import { Link } from 'react-router-dom';
import Modal from '../../app/components/Modal';
import history from '../../app/components/history';
import { fetchPortfolio, deletePortfolio } from '../actions';

class PortfolioDelete extends React.Component {
  componentDidMount() {
    this.props.fetchPortfolio(this.props.match.params.id);
  }

  renderActions() {
    const { id } = this.props.match.params;

    return (
      <React.Fragment>
        <button
          onClick={() => this.props.deletePortfolio(id)}
          className="ui button negative"
        >
          Delete
        </button>
        <Link to="/portfolios" className="ui button">
          Cancel
        </Link>
      </React.Fragment>
    );
  }

  renderContent() {
    if (!this.props.portfolio) {
      return 'Are you sure you want to delete this portfolio?';
    }

    return `Are you sure you want to delete this portfolio: ${this.props.portfolio.institudeName} ${this.props.portfolio.portfolioNo}`;
  }

  render() {
    return (
      <Modal
        title="Delete Portfolio"
        content={this.renderContent()}
        actions={this.renderActions()}
        onDismiss={() => history.push('/portfolios')}
      />
    );
  }
}

const mapStateToProps = (state, ownProps) => {
  return { portfolio: state.portfolios[ownProps.match.params.id] };
};

export default connect(
  mapStateToProps,
  { fetchPortfolio, deletePortfolio }
)(PortfolioDelete);
